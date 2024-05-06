import axios from 'axios';

const API_URL = 'http://localhost:8080/clients';

const getClients = async () => {
    return await axios.get(API_URL);
};

const getClient = async (id) => {
    return await axios.get(`${API_URL}/${id}`);
};

export const addClient = async (client) => {
    return await axios.post(API_URL, client);
};

const deleteClient = async (id) => {
    return await axios.delete(`${API_URL}/${id}`);
};

const updateClient = async (client) => {
    return await axios.put(`${API_URL}/${client.id}`, client);
};

export const ClientService = {
    getClients,
    getClient,
    addClient,
    deleteClient,
    updateClient
};
