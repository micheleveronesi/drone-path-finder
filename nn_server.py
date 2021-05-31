import io
import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
import numpy as np
import pickle
from flask import Flask, request

class CPU_Unpickler(pickle.Unpickler):
    def find_class(self, module, name):
        if module == 'torch.storage' and name == '_load_from_bytes':
            return lambda b: torch.load(io.BytesIO(b), map_location='cpu')
        else: return super().find_class(module, name)

class MLP(nn.Module):
    def __init__(self, in_dim, layers_size, activation='sigmoid', lr=8e-1, 
                 init_kind='zeros', dropout_rate=0.0):
        super(MLP, self).__init__()
        self.in_dim = in_dim
        self.lr = lr
        self.dropout = nn.Dropout(dropout_rate)
        self.activation = self._get_activation(activation)
        self.layers = self._get_layers(layers_size)
        self._init_weights_and_biases(init_kind)
        self._compile()

    def _get_layers(self, layers_size):
        layers = []
        in_dim = self.in_dim
        for idx_l, l_size in enumerate(layers_size):
            layers += [nn.Linear(in_dim, l_size)]
            if idx_l < len(layers_size) - 1:
                layers += [self.activation]
                layers += [self.dropout]
            in_dim = l_size
        return nn.Sequential(*layers)

    # tipi di funzioni di attivazione disponibili
    def _get_activation(self, act_kind):
        if act_kind == 'relu':
            return nn.ReLU()
        elif (act_kind is None) or (act_kind == 'linear'):
            return nn.Identity()
        elif act_kind == 'sigmoid':
            return nn.Sigmoid()
        elif act_kind == 'tanh':
            return nn.Tanh()
        else:
            raise Exception(f'Error. Activation "{act_kind}" is not supported.')

    def _compile(self, lr=1e-3):
        self.loss = nn.CrossEntropyLoss()
        self.optimizer = optim.Adam(self.parameters(), lr=self.lr)

    def _init_weights_and_biases(self, init_kind):
        for l in self.layers:
            if not (type(l) == nn.Linear):
                continue
            if init_kind == 'zeros':
                torch.nn.init.zeros_(l.weight)
            elif init_kind == 'uniform':
                torch.nn.init.uniform_(l.weight, a=-0.1, b=0.1)
            elif init_kind == 'normal':
                torch.nn.init.normal_(l.weight, mean=0.0, std=1e-3)
            elif init_kind == 'xavier':
                torch.nn.init.xavier_uniform_(l.weight)
            l.bias.data.fill_(0.00)

    def forward(self, x):
        x = self.layers(x)
        return x

model = None
app = Flask(__name__)

@app.route("/predict/", methods = ["POST"])
def predict():
    global model
    data = np.array(request.get_json()['reflectance']).astype(float)
    if len(data) != 2000:
        return "error"
    if model == None:
        print("MODEL LOADING")
        with open("neural_network.pkl", "rb") as f:
            model = CPU_Unpickler(f).load()
    device = torch.device("cpu")
    x = torch.from_numpy(data).float().to(device)
    pred = model(x).cpu().detach().numpy()
    class_pred = np.argmax(pred)
    return str(class_pred)


if __name__ == '__main__':
	app.run(debug=True)
