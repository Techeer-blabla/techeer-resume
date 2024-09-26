/* eslint-disable @typescript-eslint/no-unused-vars */
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import profile from "../assets/profile.svg";
import logo from "../assets/logo.svg";

function Navbar() {
  const navigate = useNavigate();
  // eslint-disable-next-line @typescript-eslint/ban-ts-comment
  // @ts-expect-error
  const [searchText, setSearchText] = useState<string>("");
  // eslint-disable-next-line @typescript-eslint/ban-ts-comment
  // @ts-expect-error
  const [userName, setUserName] = useState<string>("김테커"); //임시

  const handlesearchBar = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchText(e.target.value);
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
    <div className="w-full h-12 px-4 bg-transparent">
      <div className="flex flex-row justify-between items-center">
        {/* 로고 */}
        <img
          src={logo}
          alt="logo"
          className="w-auto h-6 hover:cursor-pointer"
          onClick={moveToMainPage}
        />

        {/* 검색 바 */}
        <div className="flex-grow max-w-lg mx-4">
          <div className="mw-140 h-10 bg-[#FBFAFE] border border-[#CDCCCC] border-solid rounded-3xl flex items-center px-4">
            <input
              id="search-box"
              className="flex-1 bg-transparent text-gray-950 placeholder-gray-500 outline-none lg:text-base"
              placeholder="검색어를 입력하세요."
              aria-label="search-box"
              autoComplete="off"
              name="search"
              value={searchText}
              onChange={moveToSearchPage}
            />
          </div>
        </div>

        {/* 프로필 */}
        <div className="flex items-center">
          <img src={profile} alt="profile" className="w-10 h-10 mx-2" />
          <p>{userName}</p>
        </div>
      </div>
      <div className="mt-3 w-full h-[1px] bg-gray-300" />
    </div>
  );
}

export default Navbar;
