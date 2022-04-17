import 'bootstrap/dist/css/bootstrap.css'
import './styles/App.css';
import React, {useEffect, useState} from 'react';
import {BrowserRouter} from "react-router-dom";
import {AuthContext} from "./context";
import AppRouter from "./AppRouter";


function App() {
    const [auth, setAuth] = useState(false);

    useEffect(() => {
        if (localStorage.getItem('auth')) {
            setAuth(true);
        }
    }, [])



    return (
        <AuthContext.Provider value={{
            auth,
            setAuth
        }}>
            <BrowserRouter>
                <AppRouter/>
            </BrowserRouter>
        </AuthContext.Provider>
    );
}

export default App;
