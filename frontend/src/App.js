
// App.js
import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import NavBar from "./components/Navbar";
import Home from "./routes/Home";
import Product from "./routes/Product";
import Start from "./routes/Start"
import Login from "./routes/Login"
import LoginHandler from "./routes/Login"
import ShoeDetail from "./routes/ShoeDetail";
import Join from "./routes/Join";
import Logout from "./routes/Logout";
import "./App.css"; // App 컴포넌트에 대한 스타일링을 위한 CSS 파일
import MyPage from "./routes/MyPage";
import MyShoes from "./routes/MyShoes";
import WriteReview from "./routes/WriteReview";
import WriteReviewForShoe from "./routes/WriteReviewForShoe";
import EditReviewModal from "./routes/EditReviewModal";

//import Profile from "./Profile";

const App = () => {
    return (
        <Router>
            <div>
                <NavBar />
                <Routes>
                    <Route path="/" element={<Start />} />
                    <Route path="/shoes" element={<Product />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/home" element={<Home />} />
                    <Route path="/myPage" element={<MyPage />} />
                    <Route path="/myShoes" element={<MyShoes />} />
                    <Route path="/kakaoLogin" //redirect_url
                        element={<LoginHandler />} //당신이 redirect_url에 맞춰 꾸밀 컴포넌트
                    />
                    <Route path="/shoes/:shoeId" element={<ShoeDetail />} />
                    <Route path="/logout" element={<Logout />} />
                    <Route path="/join" element={<Join />} />
                    <Route path="/writeReview" element={<WriteReview />} />
                    <Route path="/write-review-for-shoe" element={<WriteReviewForShoe />} />
                    <Route path="/edit-review-modal" element={<EditReviewModal />} />
                    {/*<Route path="/profile" component={Profile} />*/}
                </Routes>
            </div>
        </Router>
    );
};

export default App;