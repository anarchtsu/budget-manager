import React, {useContext, useState} from 'react';
import {AuthContext} from '../context'
import axios from '../api/Axios';
import {Link, useNavigate} from "react-router-dom";

const Login = () => {
    const navigate = useNavigate()
    const {auth, setAuth} = useContext(AuthContext)

    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [errMessage, setErrMessage] = useState('')

    const login = () => {
        axios.post('/login', null, {
            params: {
                account_login: email,
                account_password: password
            }
        }).then(r => {
            setAuth(true)
            setEmail('')
            setPassword('')
            localStorage.setItem('auth', 'true')
            console.log('1 auth = ', auth)
            navigate('/finances')
            console.log('2 auth = ', auth)
        }).catch(err => {
            console.log(err)
            // setErrMessage(err.response.message)
        });
    }

    return (
        <div id="login_div">
            <form onSubmit={e => {
                e.preventDefault()
                login()
            }}>
                <p style={{color: 'red'}}>{errMessage}</p>
                <div>
                    <label htmlFor="email">Email</label>
                    <input
                        type="email"
                        id="email"
                        placeholder="Email"
                        onChange={(e) => {
                            setEmail(e.target.value)
                        }}
                        value={email}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="password">Password</label>
                    <input
                        type="password"
                        id="password"
                        placeholder="Password"
                        onChange={(e) => {
                            setPassword(e.target.value)
                        }}
                        value={password}
                        required
                    />
                </div>
                <button type="submit">Sign in</button>
            </form>
            <p>Need an Account?<br/>
                <span><Link to="/register">Sign Up</Link></span>
            </p>
        </div>
    );
};

export default Login;
