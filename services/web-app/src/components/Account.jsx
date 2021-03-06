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
            localStorage.setItem('auth', 'true')
            setAuth(true)
            setEmail('')
            setPassword('')
            setErrMsg('')
            navigate('/finances')
        }).catch(error => {
            setErrMsg("Неверный логин или пароль")
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
        <div className="card account">
            {errMsgDiv}
            <form onSubmit={e => {
                e.preventDefault()
                isLogin ? login() : register()
            }}>
                <div>
                    <label htmlFor="email">Эл. почта:</label>
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
                    <label htmlFor="password">Пароль:</label>
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
                <a className="action-link" onClick={e => {
                    e.preventDefault()
                    isLogin ? redirect('/register') : redirect('/login')
                }}>{isLogin ? "Зарегистрироваться?" : "Авторизоваться?"}</a>
                <button type="submit">{isLogin ? "Войти" : "Создать аккаунт"}</button>
            </form>
        </div>
    );
};

export default Account;
