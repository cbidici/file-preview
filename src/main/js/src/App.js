import { useEffect, useState, useRef } from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import './App.css';
import ThumbnailView from './ThumbnailView';
require('bootstrap');


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
  if(previewResource.type === "IMAGE") {
    preview = <img display={isPreviewLoading ? "none" : "block"} className="image_big" alt="" style={{pointerEvents: 'auto'}} src={previewResource.previewUrl} onLoad={onImageLoad} onError={onImageLoad} />
  } else if(previewResource.type === "VIDEO") {
    preview =
      <video display={isPreviewLoading ? "none" : "block"} ref={videoRef} id="" width="100%" height="100%" style={{backgroundColor:"#f8f9fa", pointerEvents: 'auto'}} controls autoPlay onCanPlay={onImageLoad} onEmptied={onImageLoad}>
        <source src={previewResource.url} type="video/mp4" />
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
                  <div className="spinner-border text-light" role="status" style={{width:"5rem", height:"5rem"}}>
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


function BreadCrumb({path, setPath}) {
  const breadCrumbs = () => {
    let result = [{"name": "Home", "path":""}];
    let generated = path.split("/").filter(crumb => crumb.length > 0).map(function (val, index) { return {"name":val, "path":this.accPath += index === 0 ? val : "/"+val}; }, { accPath: "" });
    result.push(...generated);
    return result;
  };

  const breadCrumbList = !breadCrumbs() || breadCrumbs().length === 0 ? <></> :
    breadCrumbs().map((bc, index) =>
      <li key={index} className="breadcrumb-item">
        <button type="button" className="btn btn-link" onClick={() => setPath(bc.path)}>{bc.name}</button>
      </li>
  );

  return (
    <div className="container-fluid">
        <nav aria-label="breadcrumb" style={{marginTop: 1 + 'em'}}>
            <ol className="breadcrumb">
                {breadCrumbList}
            </ol>
        </nav>
    </div>
  );
}

function Gallery({path, setPath}) {

  const observerTarget = useRef(null);
  const [resources, setResources] = useState([]);
  const [index, setIndex] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const [page, setPage] = useState({offset:0, size:10});

  const setPreviewResourceByIndex = (index) => {
    if(index == null) {
      setIndex(null);
      return;
    }

    if(index<0) {
      setIndex(null);
    } else if(index < resources.length) {
      setIndex(index);
    }
    else {
      fetchData(path, page)
        .then(res => {
          if(res.resources.length > 0) {
            setPage({...page, offset:page.offset+page.size});
            setResources(prevResources => [...prevResources, ...res.resources]);
            setIndex(index);
          } else {
            setIndex(null);
          }
        });
    }
  };

  const setPathClearResources = (path) => {
    setIsLoading(false);
    setResources([]);
    setPath(path);
    setPage({offset:0, size:10});
    setIndex(null);
  };

  const fetchData = async (fetchPath, fetchPage, signal) => {
    setIsLoading(true);
    setError(null);

    if(fetchPath.length>0) {
      fetchPath = "/"+fetchPath;
    }

    try {
      return await fetch('/api/v1/resources'+fetchPath+'?offset='+fetchPage.offset+'&size='+fetchPage.size, { signal })
        .then(res => res.json())
        .then((data) => {
          return data;
        });
    } catch (error) {
      setIsLoading(false);
      setError(error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    const controller = new AbortController();
    const signal = controller.signal;
    let observerTargetCurrent;
    const observer = new IntersectionObserver(
      entries => {
        if (entries[0].isIntersecting) {
          let fetchedResources = fetchData(path, page, signal);
          fetchedResources.then(res => {
            if(res && res.resources.length > 0) {
              setPage({...page, offset:page.offset+page.size});
              setResources(prevResources => [...prevResources, ...res.resources]);
            }
          });
        }
      },
      { threshold: 0.1 }
    );

    if (observerTarget.current) {
      observer.observe(observerTarget.current);
      observerTargetCurrent = observerTarget.current
    }

    return () => {
      if (observerTargetCurrent) {
        observer.unobserve(observerTargetCurrent);
      }
      controller.abort();
    };
  }, [path, resources, page]);

  const thumbnails = resources.map((resource, index) =>
    <div key={index} className="col">
      <ThumbnailView resource={resource} setPath={setPathClearResources} setPreviewResource={setPreviewResourceByIndex} index={index} />
      <div className="name_container card-body">{resource.name}</div>
    </div>
  );

  return (
    <>
      <header>
        <div className="navbar navbar-dark bg-dark shadow-sm">
          <div className="container-fluid">
            <a href="/" className="navbar-brand d-flex align-items-center">
              <svg xmlns="http://www.w3.org/2000/svg" width={20} height={20} fill="none" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} aria-hidden="true" className="me-2" viewBox="0 0 24 24"><path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z" /><circle cx={12} cy={13} r={4} /></svg>
              <strong>{"Bıdıcı's Gallery"}</strong>
            </a>
          </div>
        </div>
      </header>
      <main>
        <BreadCrumb path={path} setPath={setPathClearResources} />
        <div className="album py-5 bg-light">
          <div className="container-fluid">
            <div className="row row-cols-auto">
              {thumbnails}
              {!isLoading && <div ref={observerTarget}></div>}
            </div>
            {isLoading &&
              <div className="d-flex justify-content-center">
                <div className="spinner-border text-dark" role="status" style={{width:"5rem", height:"5rem"}}>
                  <span className="sr-only"></span>
                </div>
              </div>
            }
            {error && <p>Error: {error.message}</p>}
          </div>
        </div>
        {index != null && <Preview previewResource={resources[index]} setPreviewResource={setPreviewResourceByIndex} index={index} />}
      </main>
      <footer className="text-muted py-5">
        <div className="container-fluid">
          <p className="mb-1">Made in Bıdıcı's with Love!</p>
        </div>
      </footer>
    </>
  );
}

function App() {
  const [currentPath, setCurrentPath] = useState("");
  const setAndLogPath = (path) => {
    setCurrentPath(path);
  };

  return (<Gallery path={currentPath} setPath={setAndLogPath} />);
}

export default App;
