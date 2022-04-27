import React, {useContext, useState} from 'react';
import {Link, Navigate, Outlet, Route, Routes} from "react-router-dom";
import Login from "./pages/Login";
import Finances from "./components/Finances";
import Statistics from "./components/Statistics";
import {AuthContext} from "./context";
import Register from "./pages/Register";
import axios from "./api/Axios";
import NotFoundPage from "./pages/NotFoundPage";

function Navbar() {
    const {auth, setAuth} = useContext(AuthContext);
    const [dropdownVisible, setDropdownVisible] = useState(false);
    const toggleMenu = (e) => {
        e.preventDefault()
        setDropdownVisible(!dropdownVisible)
    }
    const logout = async () => {
        console.log("test")
        const response = await axios.post("/logout")
        setAuth(false)
        localStorage.removeItem('auth')
        // todo redirect
    }

    return (
        <div>
            <nav>
                <div className="left">
                    <Link className="link" to="/finances">Finances</Link>
                    <Link className="link" to="/statistics">Statistics</Link>
                </div>

                <div className="dropdown right">
                    <div className="dropdown__title link" onClick={e => toggleMenu(e)}>
                        Account
                    </div>
                    <div className={`dropdown__menu ${dropdownVisible ? "open" : ""}`}>
                        {auth ?
                            <div>
                                <Link className="link" to="/edit">Edit</Link>
                                <a className="link" onClick={logout}>Logout</a>
                            </div>
                            :
                            <div>
                                <Link className="link" to="/login">Login</Link>
                                <Link className="link" to="/register">Register</Link>
                            </div>
                        }
                    </div>
                </div>
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
    const {auth, setAuth} = useContext(AuthContext);
    console.log(auth)
    return (
        <Routes>
            {auth ?
                <Route path="/" element={<DefaultLayout/>}>
                    <Route index element={<Finances/>}/>
                    <Route path="finances" element={<Finances/>}/>
                    <Route path="statistics" element={<Statistics/>}/>
                    {/*<Route path="account" element={<Statistics/>}/>*/}

                    <Route path="not-found" element={<NotFoundPage/>}/>
                    <Route path="*" element={<Navigate to="/not-found"/>}/>
                </Route>
                :
                <Route path="/" element={<DefaultLayout/>}>
                    <Route index element={<Login/>}/>
                    <Route path="login" element={<Login/>}/>
                    <Route path="register" element={<Register/>}/>

                    <Route path="not-found" element={<NotFoundPage/>}/>
                    <Route path="*" element={<Navigate to="/not-found"/>}/>
                </Route>
            }
        </Routes>
    );
};

export default AppRouter;