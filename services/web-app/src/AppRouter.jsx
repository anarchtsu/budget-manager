import React, {useContext, useEffect, useState} from 'react';
import {Link, Navigate, Outlet, Route, Routes, useNavigate} from "react-router-dom";
import Finances from "./components/Finances";
import Statistics from "./components/Statistics";
import {AuthContext} from "./context";
import axios from "./api/Axios";
import NotFound from "./components/NotFound";
import Account from "./components/Account";

function Navbar() {
    const navigate = useNavigate();
    const {auth, setAuth} = useContext(AuthContext);
    const [dropdownVisible, setDropdownVisible] = useState(false);

    const toggleMenu = () => {
        setDropdownVisible(!dropdownVisible)
    }

    const logout = () => {
        axios.post("/logout").then(r => {
            setAuth(false)
            localStorage.removeItem('auth')
            navigate('/login')
        })
    }

    return (
        <div>
            <nav>
                <div className="left">
                    <Link className="link" to="/finances">Доходы и расходы</Link>
                    <Link className="link" to="/statistics">Статистика</Link>
                </div>
                {auth ?
                    <div>
                        <a className="link" onClick={logout}>Выйти из аккаунта</a>
                    </div>
                    :
                    <div>
                        <Link className="link" to="/login">Войти</Link>
                        <Link className="link" to="/register">Регистрация</Link>
                    </div>
                }
            </nav>
        </div>
    );
}

function DefaultLayout() {
    return (
        <div>
            <Navbar/>
            <main>
                <Outlet/>
            </main>
        </div>
    );
}

const AppRouter = () => {
    const {auth} = useContext(AuthContext);

    return (
        <Routes>
            {auth ?
                <Route path="/" element={<DefaultLayout/>}>
                    <Route index element={<Finances/>}/>
                    <Route path="finances" element={<Finances/>}/>
                    <Route path="statistics" element={<Statistics/>}/>
                    <Route path="not-found" element={<NotFound/>}/>
                    <Route path="*" element={<Navigate to="/not-found"/>}/>
                </Route>
                :
                <Route path="/" element={<DefaultLayout/>}>
                    <Route index element={<Account isLogin={true}/>}/>
                    <Route path="login" element={<Account isLogin={true}/>}/>
                    <Route path="register" element={<Account isLogin={false}/>}/>
                    <Route path="not-found" element={<NotFound/>}/>
                    <Route path="*" element={<Navigate to="/not-found"/>}/>
                </Route>
            }
        </Routes>
    );
};

export default AppRouter;