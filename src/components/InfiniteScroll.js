import { useEffect, useRef } from "react";

function InfiniteScroll({getMore, hasMore, isLoading, children, dataLength}) {
    const loaderRef = useRef(null);
  
    useEffect(() => {
      const currentLoaderRef = loaderRef.current;
      const observer = new IntersectionObserver((entries) => {
        const target = entries[0];
        if (target.isIntersecting) {
            if(hasMore && !isLoading) {
                getMore(dataLength);
            }
        }
      });
  
      if (currentLoaderRef) {
        observer.observe(currentLoaderRef);
      }
  
      return () => {
        if (currentLoaderRef) {
          observer.unobserve(currentLoaderRef);
        }
      };
    });
  
    return (
        <>
          {children}
          <div ref={loaderRef}></div>
        </>
    );
  };
  
  export default InfiniteScroll;