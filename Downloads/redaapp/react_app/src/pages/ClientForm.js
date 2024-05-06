import React, { useState } from 'react';
import { addClient } from '../services/ClientService';

const ClientForm = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await addClient({ name, email, password });
            setName('');
            setEmail('');
            setPassword('');
            alert('Client added successfully!');
        } catch (error) {
            console.error('Error adding client:', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="text"
                placeholder="Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
            />
            <input
                type="email"
                placeholder="Email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
            />
            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button type="submit">Add Client</button>
        </form>
    );
};

export default ClientForm;
