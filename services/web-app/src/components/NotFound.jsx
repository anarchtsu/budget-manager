import React, {useContext} from 'react';
import {AuthContext} from "../context";

const NotFound = () => {
    const {auth} = useContext(AuthContext)
    return (
        <div className="card">
            <h2>{auth ? "Страница не найдена." : "Необходимо авторизоваться."}</h2>
        </div>
    );
};

export default NotFound;
