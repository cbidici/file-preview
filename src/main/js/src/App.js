import { useEffect, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import './App.css';
import axios from 'axios';
require('bootstrap');

function ThumbnailView({resource, setPath, setPreviewResource}) {
  let thumbnail;
  if(resource.type === 'DIRECTORY') {
    thumbnail =
      <button type="button" className="btn btn-link" onClick={() => setPath(resource.path)}>
        <svg style={{padding:2+"em"}} xmlns="http://www.w3.org/2000/svg" width="100%" height="100%" fill="currentColor" viewBox="0 0 16 16">
            <path d="M.54 3.87L.5 3a2 2 0 0 1 2-2h3.672a2 2 0 0 1 1.414.586l.828.828A2 2 0 0 0 9.828 3h3.982a2 2 0 0 1 1.992 2.181l-.637 7A2 2 0 0 1 13.174 14H2.826a2 2 0 0 1-1.991-1.819l-.637-7a1.99 1.99 0 0 1 .342-1.31zM2.19 4a1 1 0 0 0-.996 1.09l.637 7a1 1 0 0 0 .995.91h10.348a1 1 0 0 0 .995-.91l.637-7A1 1 0 0 0 13.81 4H2.19zm4.69-1.707A1 1 0 0 0 6.172 2H2.5a1 1 0 0 0-1 .981l.006.139C1.72 3.042 1.95 3 2.19 3h5.396l-.707-.707z"/>
        </svg>
      </button>
  } else if(resource.type === "IMAGE") {
    thumbnail =
      <button type="button" onClick={() => setPreviewResource(resource)}>
        <div className="image">
          <img src={resource.thumbnailUrl} alt="" className="img img-responsive full-width" />
        </div>
      </button>
  } else if(resource.type === "VIDEO") {
    thumbnail =
      <button type="button" onClick={() => setPreviewResource(resource)}>
        <div className="image">
          <svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" fill="currentColor" className="bi bi-play-circle play_button" viewBox="0 0 16 16">
            <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
            <path d="M6.271 5.055a.5.5 0 0 1 .52.038l3.5 2.5a.5.5 0 0 1 0 .814l-3.5 2.5A.5.5 0 0 1 6 10.5v-5a.5.5 0 0 1 .271-.445z"/>
          </svg>
          <img src={resource.thumbnailUrl} alt="" className="img img-responsive full-width" />
        </div>
      </button>
  }

  return (
    <div className="card img_container shadow-sm">
      {thumbnail}
    </div>
  );
}

function Preview({previewResource, setPreviewResource}) {
  if(!previewResource) {
    return <></>
  }

  let preview;
  if(previewResource.type === "IMAGE") {
    preview = <img className="image_big" alt="" style={{pointerEvents: 'auto'}} src={previewResource.previewUrl} />
  } else if(previewResource.type === "VIDEO") {
    preview =
      <video id="" width="100%" height="100%" style={{backgroundColor:"#f8f9fa"}} controls="" autoplay="">
        <source src={previewResource.url} type="video/mp4" />
        Your browser does not support the video tag.
      </video>
  }

  return (
    <div className="modal show" id="imageModal" tabIndex="-1" style={{display: 'block', backgroundColor: 'rgba(0, 0, 0, 0.9)'}}  onClick={() => setPreviewResource()}>
      <div className="modal-dialog modal-xxl">
        <div className="image_div_container d-flex align-items-center">
          <div className="image_modal_container d-flex align-items-center" onClick={e => e.stopPropagation()}>
            <button type="button" className="close" onClick={() => setPreviewResource()}>
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-x-square close_button" viewBox="0 0 16 16">
                <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"></path>
                <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"></path>
              </svg>
            </button>
            {preview}
            <div className="caption_container"><a id="caption" download={previewResource.name} href={previewResource.url} onClick={() => setPreviewResource()}>{previewResource.name}</a></div>
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
  const [resources, setResources] = useState([]);
  const [previewResource, setPreviewResource] = useState();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
      setLoading(true);
      let unmounted = false;
      let source = axios.CancelToken.source();
      axios.get('/api/v1/resources/'+path, {
        cancelToken: source.token
      })
      .then(response => {
        if (!unmounted) {
          setResources(response.data.resources);
          setLoading(false);
        }
      })
      .catch(function (e) {
        if (!unmounted) {
          setLoading(false);
          if (axios.isCancel(e)) {
            console.log('request cancelled:'+e.message);
          } else {
            console.log('another error happened:' + e.message);
          }
        }
      });
      return function () {
        unmounted = true;
        source.cancel("Cancelling in cleanup");
      };
  }, [path]);


  /*useEffect(() => {
    const abortController = new AbortController();
    const signal = abortController.signal;

    (async () => {
      try {
        setLoading(true)
        const response = await fetch(
          '/api/v1/resources/'+path,
          { signal }
        );

        if (!signal.aborted) {
          if (response.ok) {
            const data = await response.json();
            setResources(data.resources);
          } else {
            console.error('HTTP error! Status:' + response.status);
          }
        }
      } catch (error) {
        if (!signal.aborted) {
          console.error(error);
        }
      }
    })();

    return () => {
      abortController.abort();
    };
   }, [path]);*/

  const thumbnails = resources.map((resource, index) =>
    <div key={index} className="col">
      <ThumbnailView resource={resource} setPath={setPath} setPreviewResource={setPreviewResource} />
      <div className="name_container card-body">{resource.name}</div>
    </div>
  );

  let content;
  if(loading) {
    console.log(loading)
    content = <div style={{textAlign:"center"}}>Loading...</div>
  } else {
    console.log(loading)
    content =
      <div className="row row-cols-auto">
        {thumbnails}
      </div>
  }

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
        <BreadCrumb path={path} setPath={setPath} />
        <div className="album py-5 bg-light">
          <div className="container-fluid">
            {content}
          </div>
        </div>
        <Preview previewResource={previewResource} setPreviewResource={setPreviewResource} />
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
