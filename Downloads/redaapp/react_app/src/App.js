import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ClientList from './pages/ClientList';
import ClientForm from './pages/ClientForm';

const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/clients" element={<ClientList />} />
                <Route path="/clients/add" element={<ClientForm />} />
                <Route path="/clients/:id/edit" element={<ClientForm />} />
            </Routes>
        </Router>
    );
};

const Home = () => {
    return (
        <div>
            <h1>Welcome to the Home Page</h1>
            {/* Add any content you want to display on the home page */}
        </div>
    );
};

export default App;
