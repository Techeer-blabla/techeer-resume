/* eslint-disable @typescript-eslint/no-unused-vars */
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import profile from "../assets/profile.svg";
import logo from "../assets/logo.svg";
import search from "../assets/search-normal.svg";
import api from "../baseURL/baseURL.ts";
import useSearchStore from "../store/SearchStore.ts";

function Navbar() {
  const navigate = useNavigate();
  const [searchText, setSearchText] = useState<string>(""); // 검색어 상태 관리

  // eslint-disable-next-line @typescript-eslint/ban-ts-comment
  // @ts-expect-error
  const [userName, setUserName] = useState<string>("김테커"); //임시
  const { searchResults } = useSearchStore();

  // 메인 페이지로 이동 ('/')
  const moveToMainPage = () => {
    navigate("/");
  };

  const searchName = async () => {
    if (searchText === "") {
      alert("검색어를 입력해주세요!");
      return;
    }

    try {
      const response = await api.get(`search?user_name=${searchText}`);
      // const response = await api.get("/search", {
      //   params: {
      //     user_name: searchText,
      //   },
      // });

      if (response.data.length !== 0) {
        // useSearchStore.searchResults(response.data);
        console.log(response.data);
        navigate("/search");
      }
    } catch (error) {
      console.log(error);
      console.log(searchText);
    }
  };

  // 엔터 키 입력 감지하여 검색 실행
  const handleKeyPress = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      searchName();
    }
  };

  return (
    <div className="w-full h-12 px-4 bg-transparent">
      <div className="flex flex-row justify-between items-center">
        {/* 로고 */}
        <div className="pl-10">
          <img
            src={logo}
            alt="logo"
            className="w-auto h-6 hover:cursor-pointer"
            onClick={moveToMainPage}
          />
        </div>

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
              onChange={(e) => setSearchText(e.target.value)}
              onKeyPress={handleKeyPress}
            />
            <img
              src={search}
              alt="search"
              className="mr-1 w-auto h-5 hover:cursor-pointer"
              onClick={searchName}
            />
          </div>
        </div>

        {/* 프로필 */}
        <div className="flex items-center pr-10">
          <img src={profile} alt="profile" className="w-10 h-10 mx-2" />
          <p>{userName}</p>
        </div>
      </div>
      <div className="mt-3 w-full h-[1px] bg-gray-300" />
    </div>
  );
}

export default Navbar;
