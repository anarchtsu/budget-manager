import './styles/App.css';
import React, {useEffect, useState} from 'react';
import {BrowserRouter} from "react-router-dom";
import {AuthContext} from "./context";
import AppRouter from "./AppRouter";

function App({authSource}) {
    const [auth, setAuth] = useState(authSource)

    // useEffect(() => {
    //     console.log("useEffect")
    //     if (localStorage.getItem('auth')) {
    //         setAuth(true)
    //     }
    // }, [])

    return (
        <AuthContext.Provider value={{auth, setAuth}}>
            <BrowserRouter>
                <AppRouter/>
            </BrowserRouter>
        </AuthContext.Provider>
    );
}

export default App;
