import * as React from "react";
import * as SliderPrimitive from "@radix-ui/react-slider";
// import { cn } from "@/lib/utils";

export default function ExperienceSlider() {
  // 타입 정의 추가: number 배열
  const [value, setValue] = React.useState<number[]>([0, 10]);

  return (
    <div className="w-full max-w-sm space-y-4">
      {/* <h2 className="text-lg font-semibold"># 경력</h2> */}
      <div className="flex justify-between">
        <span>신입</span>
        <span>10년</span>
      </div>
      <SliderPrimitive.Root
        className="relative flex w-full touch-none select-none items-center"
        value={value}
        onValueChange={setValue}
        max={10}
        step={1}
      >
        <SliderPrimitive.Track className="relative h-1 w-full grow overflow-hidden rounded-full bg-gray-200">
          <SliderPrimitive.Range className="absolute h-full bg-blue-500" />
        </SliderPrimitive.Track>
        <SliderPrimitive.Thumb className="block h-6 w-6 rounded-full border border-blue-500 bg-white ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50" />
        <SliderPrimitive.Thumb className="block h-6 w-6 rounded-full border border-blue-500 bg-white ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50" />
      </SliderPrimitive.Root>
      <div className="text-center">
        {value[0] === 0 ? "신입" : `${value[0]}년`} -{" "}
        {value[1] === 10 ? "10년 이상" : `${value[1]}년`}
      </div>
    </div>
  );
}
