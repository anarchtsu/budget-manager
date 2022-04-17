import React, {useContext} from 'react';
import {Link, Navigate, Outlet, Route, Routes} from "react-router-dom";
import Login from "./pages/Login";
import Finances from "./components/Finances";
import Statistics from "./components/Statistics";
import {AuthContext} from "./context";
import Dropdown from 'react-bootstrap/Dropdown';
import Register from "./pages/Register";
import axios from "./api/Axios";
import NotFoundPage from "./pages/NotFoundPage";

function Navbar() {
    const {auth, setAuth} = useContext(AuthContext);

    const logout = async () => {
        const response = await axios.post("/logout")
        setAuth(false)
        localStorage.removeItem('auth')
    }

    return (
        <div>
            <nav className="navbar navbar-expand-lg" id="nav">
                <div className="container">
                    <div className="collapse navbar-collapse">
                        <Link className="nav-link text-light" to="/finances">Finances</Link>
                        <Link className="nav-link text-light" to="/statistics">Statistics</Link>
                    </div>
                    <Dropdown>
                        <Dropdown.Toggle variant="success" id="dropdown-basic">
                            Account
                        </Dropdown.Toggle>
                        {auth ?
                            <Dropdown.Menu>
                                <Dropdown.Item href="/account">Edit</Dropdown.Item>
                                <Dropdown.Item onClick={logout}>Logout</Dropdown.Item>
                            </Dropdown.Menu>
                            :
                            <Dropdown.Menu>
                                <Dropdown.Item href="/login">Login</Dropdown.Item>
                                <Dropdown.Item href="/register">Register</Dropdown.Item>
                            </Dropdown.Menu>
                        }
                    </Dropdown>
                </div>
            </nav>
        </div>
    );
}

function DefaultLayout() {
    return (
        <div>
            <Navbar/>
            <main className="container">
                <Outlet/>
            </main>
        </div>
    );
}

const AppRouter = () => {
    const {auth, setAuth} = useContext(AuthContext);

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