type TagSVGProps = {
  text: string;
  isSelected: boolean;
  onClick: () => void;
};

const TagSVG = ({ text, isSelected, onClick }: TagSVGProps) => (
  <div
    className={`flex justify-center items-center cursor-pointer border rounded-lg ${
      isSelected ? "border-blue-500" : "border-transparent"
    }`}
    style={{
      boxSizing: "border-box",
      width: "121px",
      height: "33px",
      padding: "4px",
    }}
    onClick={onClick}
  >
    <svg
      xmlns="http://www.w3.org/2000/svg"
      width="121"
      height="33"
      viewBox="0 0 121 33"
      fill="none"
      className="flex-shrink-0"
    >
      <rect width="121" height="32.4206" rx="5" fill="#F3F3F3" />
      <text
        x="50%"
        y="50%"
        textAnchor="middle"
        fill="#040404"
        fontFamily="pretendard"
        fontSize="16"
        fontWeight="400"
        dy=".35em"
      >
        {text}
      </text>
    </svg>
  </div>
);

export default TagSVG;
