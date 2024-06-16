import { useEffect, useState, useRef } from 'react';
import ThumbnailView from './ThumbnailView';
import Preview from './Preview';
import BreadCrumb from './BreadCrumb';

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
            if(res.length > 0) {
              setPage({...page, offset:page.offset+page.size});
              setResources(prevResources => [...prevResources, ...res]);
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
        return await fetch(process.env.REACT_APP_FILE_SERVICE_HOST+'/api/v1/files'+fetchPath+'?offset='+fetchPage.offset+'&size='+fetchPage.size, { signal })
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
              if(res && res.length > 0) {
                setPage({...page, offset:page.offset+page.size});
                setResources(prevResources => [...prevResources, ...res]);
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
              <div className="row row-cols-auto g-1">
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

  export default Gallery;