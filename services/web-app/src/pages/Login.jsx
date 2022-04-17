import React, {useContext, useState} from 'react';
import {AuthContext} from '../context'
import axios from '../api/Axios';
import {Link} from "react-router-dom";

const Login = () => {
    const {auth, setAuth} = useContext(AuthContext);

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const login = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post('/login', null, {
                params: {
                    account_login: email,
                    account_password: password
                }
            });
            setAuth(true);
            setEmail('');
            setPassword('')
            localStorage.setItem('auth', 'true');
        } catch (error) {
            console.log('login error')
            // todo обработка ошибок
            // if (error.response.status === 401)
            //     setErrorMessage('Incorrect login or password');
            // else
            //     setErrorMessage(error.response.message);
            // errorRef.current.focus();
        }
    }

    return (
        <div id="login_div">
            <form onSubmit={login}>
                <div className="form-row">
                    <div className="form-group col-md-6">
                        <label htmlFor="email">Email</label>
                        <input
                            type="email"
                            className="form-control"
                            id="email"
                            placeholder="Email"
                            onChange={(e) => {
                                setEmail(e.target.value)
                            }}
                            value={email}
                            required
                        />
                    </div>
                    <div className="form-group col-md-6">
                        <label htmlFor="password">Password</label>
                        <input
                            type="password"
                            className="form-control"
                            id="password"
                            placeholder="Password"
                            onChange={(e) => {
                                setPassword(e.target.value)
                            }}
                            value={password}
                            required
                        />
                    </div>
                </div>
                <button type="submit" className="btn btn-primary">Sign in</button>
            </form>
            <p>Need an Account?<br/>
                <span className="line"><Link to="/register">Sign Up</Link></span>
            </p>
        </div>
    );
};

export default Login;
