import React, { useState, useEffect } from 'react';
import { ClientService } from '../services/ClientService';

const ClientList = () => {
    const [clients, setClients] = useState([]);

    useEffect(() => {
        loadClients();
    }, []);

    const loadClients = async () => {
        try {
            const response = await ClientService.getClients();
            setClients(response.data);
        } catch (error) {
            console.error('Error loading clients:', error);
        }
    };

    const handleDelete = async (id) => {
        try {
            await ClientService.deleteClient(id);
            setClients(clients.filter(client => client.id !== id));
        } catch (error) {
            console.error('Error deleting client:', error);
        }
    };

    return (
        <div>
            <h1>Client List</h1>
            <ul>
                {clients.map(client => (
                    <li key={client.id}>
                        {client.name} - {client.email}
                        <button onClick={() => handleDelete(client.id)}>Delete</button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ClientList;
