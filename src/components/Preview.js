import { useNavigate } from "react-router-dom";
import { useState, useRef, useEffect } from "react";
import { getFile } from '../service/FileService';
import './Preview.css';


function Preview({fileId, path}) {
    const navigate = useNavigate();
    const videoRef = useRef();
    const [isPreviewLoading, setPreviewLoading] = useState(true);
    const [file, setFile] = useState(null);

    useEffect(() => {
      getFile(fileId).then(data => setFile(data));
    }, [fileId]);

    useEffect(() => {
      videoRef.current?.load();
    }, [file]);

    const dismissPreview = function() {
        navigate(path);
    }

    const onImageLoad = () => {
        setPreviewLoading(false);
    };

    const prePreview = () => {
        videoRef.current?.pause();
        if(file.prevId) {
          navigate('/preview/'+file.prevId);
        }
        //if(index != null && !isPreviewLoading) setPreviewIndex(index-1);
      };
    
      const nextPreview = () => {
        videoRef.current?.pause();
        if(file.nextId) {
          navigate('/preview/'+file.nextId);
        }
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

      useEffect(() => {
        videoRef.current?.load();
      }, [file]);
    
      useKeyDown(() => {
        dismissPreview();
      }, ["Escape"]);
    
    
      useKeyDown(() => {
        prePreview();
      }, ["ArrowLeft"]);
    
      useKeyDown(() => {
        nextPreview();
      }, ["ArrowRight"]);

    const preview = function () {
        if(file == null) {
            return <></>;
        }
        if(file.type.startsWith("IMAGE")) {
            return <img hidden={isPreviewLoading} className="image_big center" alt="" style={{pointerEvents: 'auto'}} src={process.env.REACT_APP_FILE_SERVICE_HOST+'/previews/'+file.id} onLoad={onImageLoad} onError={onImageLoad} />;
        } else if(file.type.startsWith("VIDEO")) {
            return <video display={isPreviewLoading ? "none" : "block"} ref={videoRef} id="" style={{backgroundColor:"#000000", pointerEvents: 'auto', width:"100%", height:"100%", position:"absolute", top:"0", left:"0" }} controls autoPlay onCanPlay={onImageLoad} onEmptied={onImageLoad} playsInline webkit-playsinline="true">
                <source src={process.env.REACT_APP_FILE_SERVICE_HOST+'/previews/'+file.id} type="video/mp4" />
                Your browser does not support the video tag.
            </video>
        }
    };
    

    return (
        <div className="modal show" id="imageModal" tabIndex="-1" style={{display: 'block', backgroundColor: 'rgba(0, 0, 0, 1)'}}  onClick={() => dismissPreview()}>
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
                <div style={{height:"100%", position:"relative", width:"100%"}}>
                    {preview()}
                </div>
                {isPreviewLoading &&
                  <div style={{width:"100%", height:"100%"}}>
                    <div style={{top:"50%", left:"50%", position:"absolute"}}>
                      <div className="spinner-border text-light" role="status" style={{width:"5rem", height:"5rem", marginTop:"-2.5rem", marginLeft:"-2.5rem"}}>
                        <span className="sr-only"></span>
                      </div>
                    </div>
                  </div>}
                {!isPreviewLoading && <div className="caption_container"><a id="caption" download={file.name} href={file.url}>{file.name}</a></div>}
              </div>
            </div>
          </div>
        </div>
    );
}
export default Preview;