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

export default BreadCrumb;