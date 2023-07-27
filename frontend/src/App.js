import logo from './logo.svg';
import './App.css';
import { useEffect, useState } from 'react';
import axios from "axios"

function App() {
  // message 초기값 설정 (""로 설정)
  const [data, setData] = useState('');

  // useEffect(함수, 배열) : 컴포넌트가 화면에 나타났을 때 자동 실행
  /*useEffect(() => {
    // fetch(url, options) : Http 요청 함수
    fetch("/")
      .then(response => response.text())
      .then(message => {
        setMessage(message);
      });
  }, [])*/
    useEffect(() => {
        axios.get("/api/users/me")
            .then((response) => setData(response.data))
            .catch(error => console.log(error))
    }, []);
    return (
        <div className="App">
            <header className="App-header">
                <img src={logo} className="App-logo" alt="logo"/>
                백엔드에서 가져온 데이터입니다 :
                {data.email

                    /*{data.map(item =>(
                    <li>
                        <h2>{item.brand}</h2>
                        <h3>{item.name}</h3>
                        <p>{item.image}</p>
                    </li>
                ))}*/}
            </header>
        </div>
    );
}

export default App;