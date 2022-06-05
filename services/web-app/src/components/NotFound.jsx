import React, {useContext} from 'react';
import {AuthContext} from "../context";

const NotFound = () => {
    const {auth} = useContext(AuthContext)
    return (
        <div className="card">
            <h2>Страница не найдена. {auth ? "" : "Вы не авторизированы."}</h2>
        </div>
    );
};

export default NotFound;