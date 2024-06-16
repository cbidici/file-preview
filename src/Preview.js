import {useEffect, useState, useRef} from 'react';

function Preview({previewResource, setPreviewResource, index}) {
    const videoRef = useRef();
    const [isPreviewLoading, setPreviewLoading] = useState(true);
  
    const setPreviewIndex = (newIndex) => {
      setPreviewLoading(true);
      setPreviewResource(newIndex)
    };
  
    const useKeyDown = (callback, keys) => {
      useEffect(() => {
        const onKeyDown = (event) => {
          const wasAnyKeyPressed = keys.some((key) => event.key === key);
          if (wasAnyKeyPressed) {
            event.preventDefault();
            callback();
          }
        };
        document.addEventListener('keydown', onKeyDown);
        return () => {
          document.removeEventListener('keydown', onKeyDown);
        };
      });
    };
  
    const prePreview = () => {
      videoRef.current?.pause();
      if(index != null && !isPreviewLoading) setPreviewIndex(index-1);
    };
  
    const nextPreview = () => {
      videoRef.current?.pause();
      if(index != null && !isPreviewLoading) setPreviewIndex(index+1);
    };
  
    const dismissPreview = () => {
      videoRef.current?.pause();
      if(index != null && !isPreviewLoading) setPreviewIndex(null);
    };
  
    const onImageLoad = () => {
      setPreviewLoading(false);
    };
  
    useEffect(() => {
      videoRef.current?.load();
    }, [previewResource]);
  
    useKeyDown(() => {
      dismissPreview();
    }, ["Escape"]);
  
  
    useKeyDown(() => {
      prePreview();
    }, ["ArrowLeft"]);
  
    useKeyDown(() => {
      nextPreview();
    }, ["ArrowRight"]);
  
    if(index == null) {
      return <></>
    }
  
    let preview;
    if(previewResource.type.startsWith("IMAGE")) {
      preview = <img hidden={isPreviewLoading} className="image_big" alt="" style={{pointerEvents: 'auto'}} src={process.env.REACT_APP_FILE_SERVICE_HOST+'/previews/'+previewResource.id} onLoad={onImageLoad} onError={onImageLoad} />
    } else if(previewResource.type.startsWith("VIDEO")) {
      preview =
        <video display={isPreviewLoading ? "none" : "block"} ref={videoRef} id="" width="100%" height="100%" style={{backgroundColor:"#f8f9fa", pointerEvents: 'auto'}} controls autoPlay onCanPlay={onImageLoad} onEmptied={onImageLoad} playsInline webkit-playsInline>
          <source src={process.env.REACT_APP_FILE_SERVICE_HOST+'/previews/'+previewResource.id} type="video/mp4" />
          Your browser does not support the video tag.
        </video>
    }
  
    return (
      <div className="modal show" id="imageModal" tabIndex="-1" style={{display: 'block', backgroundColor: 'rgba(0, 0, 0, 0.9)'}}  onClick={() => dismissPreview()}>
        <div className="modal-dialog modal-xxl">
          <div className="image_div_container d-flex align-items-center">
            <div className="image_modal_container d-flex align-items-center" onClick={e => e.stopPropagation()}>
              <button type="button" className="close" onClick={() => dismissPreview()}>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-x-square close_button" viewBox="0 0 16 16">
                  <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"></path>
                  <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"></path>
                </svg>
              </button>
              <button disabled={isPreviewLoading} type="button" onClick={() => prePreview()}>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-chevron-left prev_button" viewBox="0 0 16 16">
                  <path fillRule="evenodd" d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
                </svg>
              </button>
              <button disabled={isPreviewLoading} type="button" onClick={() => nextPreview()}>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-chevron-right next_button" viewBox="0 0 16 16">
                  <path fillRule="evenodd" d="M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z"/>
                </svg>
              </button>
              {preview}
              {isPreviewLoading &&
                <div style={{width:"100%", height:"100%"}}>
                  <div style={{top:"50%", left:"50%", position:"absolute"}}>
                    <div className="spinner-border text-light" role="status" style={{width:"5rem", height:"5rem", marginTop:"-2.5rem", marginLeft:"-2.5rem"}}>
                      <span className="sr-only"></span>
                    </div>
                  </div>
                </div>}
              {!isPreviewLoading && <div className="caption_container"><a id="caption" download={previewResource.name} href={previewResource.url}>{previewResource.name}</a></div>}
            </div>
          </div>
        </div>
      </div>
    );
  }

export default Preview;