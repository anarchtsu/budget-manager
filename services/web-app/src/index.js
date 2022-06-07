import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';

const auth = !!localStorage.getItem('auth')

ReactDOM.render(
    <App authSource={auth}/>,
    document.getElementById('root')
);
