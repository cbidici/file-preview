async function getChildren(path, offset, size) {
    try {
        return await fetch(process.env.REACT_APP_FILE_SERVICE_HOST+'/api/v1/files/children'+path+'?offset='+offset+'&size='+size)
            .then(res => res.json())
            .then((data) => {
                return data;
            });
    } catch(error) {
        throw error;
    }
}

async function getFile(fileId) {
    try {
        return await fetch(process.env.REACT_APP_FILE_SERVICE_HOST+'/api/v1/files/'+fileId)
            .then(res => res.json())
            .then((data) => {
                return data;
            });
    } catch(error) {
        throw error;
    }
}

export {getChildren, getFile};
