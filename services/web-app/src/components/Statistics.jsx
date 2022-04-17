import React from "react";
import axios from "../api/Axios";

const Statistics = () => {

    const getStatistics = async (dateFrom, dateTo, currencyId) => {
        const statistics = await axios.get('/api/v1/statistics', null, {params: {dateFrom, dateTo, currencyId}});
        // todo отрисовать статистику через компонент
    }

    return <h1>Statistics</h1>;
}

export default Statistics;
