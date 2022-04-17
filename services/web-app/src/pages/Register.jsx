import React, {useState} from 'react';
import {Link} from "react-router-dom";
import axios from "../api/Axios";

const Register = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorMsg, setErrorMsg] = useState('');

    const register = async (event) => {
        event.preventDefault();
        await axios.post('/api/v1/accounts', null, {params:{email, password}}).then((response) => {
            // todo редирект при успехе
        }).catch((error) => {
            // console.log(error.response.status)
            // console.log(error.response)
            // console.log(error.response.data['message'])
            setErrorMsg(error.response.data['message'])
        });
    }

    return (
        <div id="login_div">
            {errorMsg === '' ?
                <p></p>
                :
                <div>
                    <p color="red">{errorMsg}</p>
                </div>
            }
            <form onSubmit={register}>
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
                <button type="submit" className="btn btn-primary">Sign Up</button>
            </form>
            <p>Already have an Account?<br/>
                <span className="line"><Link to="/register">Sign In</Link></span>
            </p>
        </div>
    );
};

export default Register;