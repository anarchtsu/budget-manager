import React, {useState} from "react";
import axios from "../api/Axios";
import StatisticsView from "./StatisticsView";
import {BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip,} from 'chart.js';

ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
);

const url = '/api/v1/statistics';

const Statistics = () => {
    const [dateFrom, setDateFrom] = useState();
    const [dateTo, setDateTo] = useState();
    const [currencyId, setCurrencyId] = useState(1);

    const [text, setText] = useState();
    const [dailyStatistics, setDailyStatistics] = useState();
    const [expensesAmount, setExpensesAmount] = useState('')
    const [incomesAmount, setIncomesAmount] = useState('')

    const getStatistics = () => {
        axios.get(url, {
            params: {
                dateFrom,
                dateTo,
                currencyId
            }
        }).then(r => {
            setDailyStatistics(r.data.dailyStatistics)
            setExpensesAmount(r.data.expensesAmount)
            setIncomesAmount(r.data.incomesAmount)
            setText('Статистика за период (' + dateFrom + ' - ' + dateTo + ')')
        }).catch(err => {
            // todo
        })
    }

    const statisticsBar = dailyStatistics && text &&
        <StatisticsView dailyStatistics={dailyStatistics} text={text}/>

    return (
        <div className="card statistics">
            <div className="statistics-filters">
                <form onSubmit={e => {
                    e.preventDefault()
                    getStatistics()
                }}>
                    <div>
                        <label htmlFor="date-from">Дата с:</label>
                        <input
                            type="date"
                            name="date-from"
                            onChange={e => {
                                e.preventDefault()
                                setDateFrom(e.target.value)
                            }}
                            value={dateFrom}
                        />
                    </div>
                    <div>
                        <label htmlFor="date-to">Дата по:</label>
                        <input
                            type="date"
                            name="date-to"
                            onChange={e => {
                                e.preventDefault()
                                setDateTo(e.target.value)
                            }}
                            value={dateTo}
                        />
                    </div>
                    <button type="submit">Рассчитать</button>
                </form>
            </div>
            {expensesAmount !== '' && incomesAmount !== '' ?
                <div>
                    <hr/>
                    <p>Общий доход: {incomesAmount} руб.</p>
                    <p>Общий расход: {expensesAmount} руб.</p>
                    <hr/>
                </div>
                :
                <div>
                </div>
            }
            <div className="statistics-bar">
                {statisticsBar}
            </div>
        </div>
    );
}

export default Statistics;
