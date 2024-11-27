function ResumeErrorFallback() {
// { click }: { click: () => void }
  return (
    <div className="flex justify-center items-center flex-col w-full my-3">
      <p className="text-center text-black text-2xl font-semibold mb-5 mt-5">
        잠시 후 다시 시도해주세요
      </p>
      <p className="text-center text-[#808080] text-md font-medium mb-10">
        요청을 처리하는데
        <br />
        실패하였습니다.
      </p>
      <button
        className="w-80 h-14 bg-[#4177ff] rounded-lg hover:shadow flex items-center justify-center"
        // onClick={click}
      >
        <p className="text-center text-white text-lg font-medium">다시 시도</p>
      </button>
    </div>
  );
}

export default ResumeErrorFallback;
