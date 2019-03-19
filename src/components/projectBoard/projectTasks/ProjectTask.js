import React, {Component} from 'react';
import {Link} from "react-router-dom";
import {deleteProjectTask} from "../../../actions/backlogActions";
import {connect} from "react-redux";
import PropTypes from "prop-types";
import {bindActionCreators} from "redux";

class ProjectTask extends Component {

    onDeleteClick(backlog_id, pt_id) {
        this.props.deleteProjectTask(backlog_id, pt_id);
    }
    render() {
        const {project_task_prop} = this.props;
        let priorityString;
        let priorityClass;
        if(project_task_prop.priority === 1){
            priorityClass="bg-danger text-light";
            priorityString="HIGH";
        }
        if(project_task_prop.priority === 2){
            priorityClass="bg-warning text-light";
            priorityString="MEDIUM";
        }
        if(project_task_prop.priority === 3){
            priorityClass="bg-info text-light";
            priorityString="LOW";
        }

        const backlog_id = project_task_prop.projectIdentifier;
        const pt_id = project_task_prop.projectSequence;

        return (
            <div className="card mb-1 bg-light">

                <div className={`card-header text-primary ${priorityClass}`}>
                    ID: {project_task_prop.projectSequence}-- Priority: {priorityString}
                </div>
                <div className="card-body bg-light">
                    <h5 className="card-title">{project_task_prop.summary}</h5>
                    <p className="card-text text-truncate ">
                        {project_task_prop.acceptanceCriteria}
                    </p>
                    <Link to={`/updateProjectTask/${backlog_id}/${pt_id}`} className="btn btn-primary">
                        View / Update
                    </Link>

                    <button
                        className="btn btn-danger ml-4"
                        onClick={this.onDeleteClick.bind(this,
                            project_task_prop.projectIdentifier, project_task_prop.projectSequence)}
                    >
                        Delete
                    </button>
                </div>
            </div>
        );
    }
}

ProjectTask.propTypes = {
    deleteProjectTask: PropTypes.func.isRequired
};

export default connect(null, {deleteProjectTask})(ProjectTask);