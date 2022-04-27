import React, {useState} from 'react';
import axios from "../../api/Axios";

const MyModal = ({onClose, financeOperation, url}) => {
    const [form, setForm] = useState(
        financeOperation ?
            financeOperation :
            {
                id: null,
                date: "",
                currencyId: 1,
                amount: 0,
                type: "INCOME",
                period: "NONE",
                categoryName: ""
            }
    );

    return (
        <div className="modal-overlay">
            <div className="modal">
                <button className="modal-close modal__button" onClick={e => {
                    e.preventDefault()
                    onClose()
                }}>X
                </button>
                <form onSubmit={e => {
                    e.preventDefault()
                    form.id === null ?
                        axios.post(url, form).then(r => onClose()) :
                        axios.put(url +'/' + form.id, form).then(r => onClose())
                }}>
                    <div>
                        <label htmlFor="date">Date</label>
                        <input
                            type="date"
                            name="date"
                            onChange={(e) => {
                                e.preventDefault()
                                setForm(prevState => ({
                                    ...prevState,
                                    date: e.target.value
                                }))
                            }}
                            value={form.date}
                        />
                    </div>
                    <div>
                        <label htmlFor="currencyId">Currency</label>
                        <select id="currencyId" name="currencyId"
                                onChange={e => {
                                    e.preventDefault()
                                    setForm(prevState => ({
                                        ...prevState,
                                        currencyId: e.target.value
                                    }))
                                }}
                                value={form.currencyId}
                        >
                            <option value="1">RUB</option>
                            <option value="2" disabled>USD</option>
                            <option value="3" disabled>EUR</option>
                        </select>
                    </div>
                    <div>
                        <label htmlFor="amount">Amount</label>
                        <input
                            type="number"
                            name="amount"
                            onChange={(e) => {
                                e.preventDefault()
                                setForm(prevState => ({
                                    ...prevState,
                                    amount: e.target.value
                                }))
                            }}
                            value={form.amount}
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="type">Operation Type</label>
                        <select id="type" name="type"
                                onChange={e => {
                                    e.preventDefault()
                                    setForm(prevState => ({
                                        ...prevState,
                                        type: e.target.value
                                    }))
                                }}
                                value={form.type}
                        >
                            <option value="INCOME">INCOME</option>
                            <option value="EXPENSE">EXPENSE</option>
                        </select>
                    </div>
                    <div>
                        <label htmlFor="period">Period</label>
                        <select id="type" name="period"
                                onChange={e => {
                                    e.preventDefault()
                                    setForm(prevState => ({
                                        ...prevState,
                                        period: e.target.value
                                    }))
                                }
                                }
                                value={form.period}
                        >
                            <option value="NONE">NONE</option>
                            <option value="DAY">DAY</option>
                            <option value="MONTH">MONTH</option>
                            <option value="YEAR">YEAR</option>
                        </select>
                    </div>
                    <div>
                        <label htmlFor="categoryName">Category</label>
                        <input
                            type="text"
                            name="categoryName"
                            onChange={(e) => {
                                e.preventDefault()
                                setForm(prevState => ({
                                    ...prevState,
                                    categoryName: e.target.value
                                }))
                            }}
                            value={form.categoryName}
                            required
                        />
                    </div>
                    <button className="modal__button" type="submit">{form.id === null ? "Create" : "Update"}</button>
                </form>
            </div>
        </div>
    );
};

export default MyModal;