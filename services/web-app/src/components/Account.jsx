import React, {useContext, useState} from 'react';
import {useNavigate} from "react-router-dom";
import {AuthContext} from "../context";
import axios from "../api/Axios";

const Account = ({isLogin}) => {
    const navigate = useNavigate()
    const {auth, setAuth} = useContext(AuthContext)

    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [errMsg, setErrMsg] = useState('')

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
            setErrMsg('')
            localStorage.setItem('auth', 'true')
            console.log('auth = ', auth)
            navigate('/finances')
        }).catch(error => {
            console.log('error in login')
            setErrMsg(error.response.data['message'])
        });
    }

    const register = () => {
        axios.post('/api/v1/accounts', null, {
            params: {
                email,
                password
            }
        }).then(r => {
            setEmail('')
            setPassword('')
            setErrMsg('')
            navigate('/login')
        }).catch(error => {
            console.log('error in register')
            setErrMsg(error.response.data['message'])
        });
    }

    const redirect = (url) => {
        setEmail('')
        setPassword('')
        setErrMsg('')
        navigate(url)
    }

    const errMsgDiv = errMsg !== '' &&
        <div>
            <p color="red">{errMsg}</p>
        </div>

    return (
        <div className="account">
            {errMsgDiv}
            <form onSubmit={e => {
                e.preventDefault()
                isLogin ? login() : register()
            }}>
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
                <button type="submit">{isLogin ? "Sign in" : "Sign Up"}</button>
            </form>
            <div>
                <p>{isLogin ? "Need an Account?" : "Already have an Account?"}</p>
                <button className="link" onClick={e => {
                    e.preventDefault()
                    isLogin ? redirect('/register') : redirect('/login')
                }}>{isLogin ? "Sign Up" : "Sign In"}</button>
            </div>
        </div>
    );
};

export default Account;
