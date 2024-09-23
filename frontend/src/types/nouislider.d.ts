declare module "nouislider" {
  interface noUiSlider {
    create: (target: HTMLElement, options: noUiSliderOptions) => void;
    on: (
      event: string,
      callback: (values: (string | number)[], handle: number) => void
    ) => void;
    destroy: () => void;
    set: (values: (string | number)[]) => void;
    get: () => (string | number)[];
  }

  interface noUiSliderOptions {
    start: number[];
    connect: boolean | boolean[];
    step: number;
    range: {
      min: number;
      max: number;
    };
    tooltips?: boolean | boolean[]; // tooltips 옵션 추가
    behaviour?: string; // behaviour 옵션 추가
  }

  const noUiSlider: {
    create: (target: HTMLElement, options: noUiSliderOptions) => void;
    on: (
      event: string,
      callback: (values: (string | number)[], handle: number) => void
    ) => void;
    destroy: () => void;
    set: (values: (string | number)[]) => void;
    get: () => (string | number)[];
  };

  export default noUiSlider;
}

// HTMLDivElement에 noUiSlider 속성 추가
interface HTMLDivElement {
  noUiSlider?: noUiSlider; // noUiSlider가 선택적으로 정의됨
}
