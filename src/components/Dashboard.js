import React, {Component} from 'react';
import ProjectItem from "./project/ProjectItem";
import CreateProjectButton from "./project/CreateProjectButton";
import PropTypes from 'prop-types';

import {connect} from "react-redux";
import {createProject, getProjects} from "../actions/projectActions";
import {bindActionCreators} from "redux";

class Dashboard extends Component {

    componentDidMount() {
        this.props.getProjects();
    }

    render() {
        const {projects} = this.props.project;
        return (
            <div className="projects">
                <div className="container">
                    <div className="row">
                        <div className="col-md-12">
                            <h1 className="display-4 text-center">Projects</h1>
                            <br/>
                            <CreateProjectButton/>
                            <br/>
                            <hr/>
                            {
                                projects.map(project =>(
                                <ProjectItem key={project.id} project={project}/>
                                ))
                            }
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

Dashboard.propTypes = {
    project: PropTypes.object.isRequired,
    getProjects: PropTypes.func.isRequired
};

function matchDispatchToProps(dispatch) {
    return bindActionCreators({getProjects: getProjects}, dispatch);
}

function mapStateToProps(state) {
    return {
        project: state.project,
    }
}

export default connect(mapStateToProps, matchDispatchToProps)(Dashboard);