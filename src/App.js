import {useState} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import './App.css';
import Gallery from './Gallery';
require('bootstrap');


function App() {
  const [currentPath, setCurrentPath] = useState("");
  const setAndLogPath = (path) => {
    setCurrentPath(path);
  };

  return (<Gallery path={currentPath} setPath={setAndLogPath} />);
}

export default App;
