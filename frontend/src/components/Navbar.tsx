/* eslint-disable @typescript-eslint/no-unused-vars */
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import profile from "../assets/profile.svg";
import logo from "../assets/logo.svg";

function Navbar() {
  const navigate = useNavigate();
  const [searchText, setSearchText] = useState<string>("");
  const [userName, setUserName] = useState<string>("김테커"); //임시

  const handlesearchBar = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchText(e.target.value);
    moveToSearchPage();
  };

  // 메인 페이지로 이동 ('/')
  const moveToMainPage = () => {
    navigate("/");
  };

  // 검색 결과 페이지로 이동 ('/search')
  const moveToSearchPage = () => {
    navigate("/search");
  };

  /*
      마이 페이지로 이동 ('/mypage/:id') - p2
      const moveToMyPage = () => {
      navigate(`/mypage/${user.id}`);
    };
    */

  return (
    <div className="flex flex-row justify-center items-center w-full h-12 bg-white">
      {/* 로고 */}
      <img
        src={logo}
        alt="logo"
        className="w-auto h-6 mx-2 hover:cursor-pointer"
        onClick={moveToMainPage}
      />

      {/* 검색 바 */}
      <div className="w-140 h-10 mx-2 bg-[#FBFAFE] border border-[#CDCCCC] border-solid rounded-3xl">
        <input
          onChange={handlesearchBar}
          id="search-box"
          className="flex items-center bg-transparent text-gray-950 placeholder-gray-500 outline-none lg:h-11 lg:text-base"
          placeholder="검색어를 입력하세요."
          aria-label="search-box"
          autoComplete="off"
          name="search"
        />
      </div>

      {/* 프로필 */}
      <div className="flex flex-row">
        <img src={profile} alt="profile" className="w-auto h-10 mx-2" />
        <p>{userName}</p>
      </div>
    </div>
  );
}

export default Navbar;
