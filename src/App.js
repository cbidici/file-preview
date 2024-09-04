import './App.css';
import Header from './Header';
import Footer from './Footer';
import { Route, Routes } from 'react-router-dom';
import Browser from './pages/Browser';

function App() {

  return (
    <>
      <Header />
      <main>
        <Routes>
          <Route path='/categories' element={<div>categories</div>} />
          <Route path='/*' element={<Browser/>} />
        </Routes>
      </main>
      <Footer />
    </>
  );
}

export default App;
