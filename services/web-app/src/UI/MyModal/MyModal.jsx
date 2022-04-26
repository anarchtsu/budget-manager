import React, {useState} from 'react';

const MyModal = ({onClose, financeOperation}) => {
    const [form, setForm] = useState(
        financeOperation ?
            financeOperation :
            {
                date: "",
                currencyCode: "RUB",
                amount: 0,
                type:"INCOME",
                period:"NONE",
                categoryName:""
            }
    );

    return (
        <div className="modal-overlay">
            <div className="modal">
                <button className="modal-close" onClick={e => onClose(e)}>X</button>
                <form>
                    <div>
                        <label htmlFor="date">Дата</label>
                        <input
                            type="text"
                            name="date"
                            onChange={(e) => {
                                setForm(prevState => {
                                    prevState.date = e.target.value
                                })
                            }}
                            value={form.date}
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="currencyCode">Валюта</label>
                        <input
                            type="text"
                            name="currencyCode"
                            onChange={(e) => {
                                setForm(prevState => {
                                    prevState.currencyCode = e.target.value
                                })
                            }}
                            value={form.currencyCode}
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="amount">Сумма</label>
                        <input
                            type="text"
                            name="amount"
                            onChange={(e) => {
                                setForm(prevState => {
                                    prevState.amount = e.target.value
                                })
                            }}
                            value={form.amount}
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="type">Тип операции</label>
                        <input type="text" name="type"/>
                    </div>
                    <div>
                        <label htmlFor="period">Повтор</label>
                        <input type="text" name="period"/>
                    </div>
                    <div>
                        <label htmlFor="categoryName">Категория</label>
                        <input type="text" name="categoryName"/>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default MyModal;