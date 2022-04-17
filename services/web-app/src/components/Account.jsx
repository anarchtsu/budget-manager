import React, {useRef, useEffect, useState, useContext} from 'react';
import AuthContext from '../context'

import axios from '../api/Axios';

const LOGIN_URL = '/login'

const Account = () => {
    const {setAuth} = useContext(AuthContext);

    const accountRef = useRef();
    const [account, setAccount] = useState('');
    const [password, setPassword] = useState('');

    const errorRef = useRef();
    const [errorMessage, setErrorMessage] = useState('');

    const [success, setSuccess] = useState(false);

    useEffect(() => {
        accountRef.current.focus();
    }, [])

    useEffect(() => {
        setErrorMessage('');
    }, [account, password])

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post(LOGIN_URL, null, {
                params: {
                    account_login: account,
                    account_password: password
                }
            });
            setAuth({account, password});
            setAccount('');
            setPassword('');
            setSuccess(true);
        } catch (error) {
            if (error.response.status === 401)
                setErrorMessage('Incorrect login or password');
            else
                setErrorMessage(error.response.message);
            errorRef.current.focus();
        }
    }

    return (
        <>
            {success ? (
                <section>
                    <h1>You are logged in!</h1>
                    <br/>
                    <p>
                        <a href="#">Go To Home</a>
                    </p>
                </section>
            ) : (
                <section>
                    <h1>Sign In</h1>
                    <p ref={errorRef} className="" aria-live="assertive">{errorMessage}</p>
                    <form onSubmit={handleSubmit}>
                        <label htmlFor="email">Email:</label>
                        <input
                            type="text"
                            id="email"
                            ref={accountRef}
                            autoComplete="off"
                            onChange={(e) => {
                                setAccount(e.target.value)
                            }}
                            value={account}
                            required
                        />

                        <label htmlFor="password">Password:</label>
                        <input
                            type="password"
                            id="password"
                            autoComplete="off"
                            onChange={(e) => {
                                setPassword(e.target.value)
                            }}
                            value={password}
                            required
                        />

                        <button>Sign In</button>
                    </form>
                    <p>
                        Need an Account?
                        <br/>
                        <span className="line">
                            {/*link*/}
                            <a href="#">Sign Up</a>
                        </span>
                    </p>
                </section>
            )}
        </>
    );
};

export default Account;
