export function scrollIntoView(el: any) {
  const htmlEle = el.$el as HTMLElement;
  if (htmlEle){
    htmlEle.scrollIntoView({ behavior: 'smooth' });
  }
}
