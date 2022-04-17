import React, {useEffect} from "react";
import axios from '../api/Axios';

const Finances = () => {
    const fetchFinanceOperations = async () => {
        await axios.get("/api/v1/finance-operations").then(response => {
            console.log(response.data);
        })
    };

    // todo

    return <h1>Finances</h1>;
}

export default Finances;
