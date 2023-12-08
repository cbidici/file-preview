import {useState} from 'react';

function ThumbnailView({resource, setPath, setPreviewResource, index}) {
  const [isThumbnailLoading, setThumbnailLoading] = useState(true);

  const onThumbnailLoad = () => {
    setThumbnailLoading(false);
  };

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
      <button type="button" disabled={isThumbnailLoading} onClick={() => setPreviewResource(index)}>
        <img display={isThumbnailLoading ? "none" : "block"} src={resource.thumbnailUrl} alt="" onLoad={onThumbnailLoad} onError={onThumbnailLoad} />
        {isThumbnailLoading &&
          <div style={{width:"100%", height:"100%"}}>
            <div style={{top:"50%", left:"50%", position:"absolute"}}>
              <div className="spinner-border text-dark" role="status" style={{width:"2rem", height:"2rem", marginLeft: "-1rem", marginTop: "-1rem"}}>
                <span className="sr-only"></span>
              </div>
            </div>
          </div>
        }
      </button>
  } else if(resource.type === "VIDEO") {
    thumbnail =
      <button type="button" disabled={isThumbnailLoading} onClick={() => setPreviewResource(index)}>
          <svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" fill="currentColor" className="bi bi-play-circle play_button" viewBox="0 0 16 16">
            <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
            <path d="M6.271 5.055a.5.5 0 0 1 .52.038l3.5 2.5a.5.5 0 0 1 0 .814l-3.5 2.5A.5.5 0 0 1 6 10.5v-5a.5.5 0 0 1 .271-.445z"/>
          </svg>
          <img display={isThumbnailLoading ? "none" : "block"} src={resource.thumbnailUrl} alt="" onLoad={onThumbnailLoad} onError={onThumbnailLoad} />
          {isThumbnailLoading &&
            <div style={{width:"100%", height:"100%"}}>
              <div style={{top:"50%", left:"50%", position:"absolute"}}>
                <div className="spinner-border text-dark" role="status" style={{width:"2rem", height:"2rem", marginLeft: "-1rem", marginTop: "-1rem"}}>
                  <span className="sr-only"></span>
                </div>
              </div>
            </div>
          }
      </button>
  }

  return (
    <div className="card img_container shadow-sm fill">
      {thumbnail}
    </div>
  );
}

export default ThumbnailView;