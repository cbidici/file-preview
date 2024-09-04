import BreadCrumb from '../components/BreadCrumb';
import Thumbnail from '../components/Thumbnail';
import { useState, useEffect } from 'react';
import { getChildren } from '../service/FileService';
import { useLocation } from 'react-router-dom';
import Preview from '../components/Preview';
import InfiniteScroll from '../components/InfiniteScroll';

function Browser() {
    const location = useLocation();
    const [files, setFiles] = useState([]);
    const [currentPath, setCurrentPath] = useState("");
    const [currentFileId, setCurrentFileId] = useState(null);
    const [hasMore, setHasMore] = useState(true);
    const [isLoading, setIsLoading] = useState(true);

    const getMore = function(offset) {
        setIsLoading(true);
        getChildren(currentPath, offset, 10)
                .then(data => {
                    if(data.length < 10) {
                        setHasMore(false);
                    }
                    setFiles([...files, ...data]);
                    setIsLoading(false);
                });
    }

    useEffect(() => {
        let path = location.pathname;
        let fileId = null;
        if(path.startsWith("/preview")) {
          fileId = path.replace('/preview/', '');
          path = '/'+fileId.substring(0, fileId.lastIndexOf('/'));
        }
    
        if(path !== currentPath) {
            setCurrentPath(path);
            getChildren(path, 0, 10)
                .then(data => {
                    if(data.length === 10) {
                        setHasMore(true);
                    }
                    setFiles(data);
                    setIsLoading(false);
                });
        }
    
        if(fileId != null &&  fileId !== currentFileId)  {
            setCurrentFileId(fileId);
        } else if(fileId == null) {
            setCurrentFileId(null);
        }
      
    }, [currentFileId, currentPath, location]);


    return (
        <>
            <div className="container-fluid">
                <BreadCrumb path={currentPath} />
                <div className="row row-cols-auto g-0">
                    <InfiniteScroll getMore={getMore} hasMore={hasMore} isLoading={isLoading} dataLength={files.length}>
                    {
                        files.map((file) => <Thumbnail key={file.id} resource={file}/>)
                    }
                    </InfiniteScroll>
                </div>
            </div>
            {currentFileId && <Preview fileId = {currentFileId} path = {currentPath} />}
        </>
    );
}

export default Browser;