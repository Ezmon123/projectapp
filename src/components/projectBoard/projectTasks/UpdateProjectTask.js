import React, {Component} from 'react';
import {Link} from "react-router-dom";
import classnames from "classnames";
import {connect} from "react-redux";
import PropTypes from "prop-types";
import {bindActionCreators} from "redux";
import {getProjectTask, updateProjectTask} from "../../../actions/backlogActions";

class UpdateProjectTask extends Component {
    constructor(props) {
        super(props);
        const {backlog_id} = this.props.match.params;
        const {pt_id} = this.props.match.params;
        this.state = {
            id: "",
            projectSequence: pt_id,
            summary: "",
            acceptanceCriteria: "",
            status: "",
            priority: "",
            dueDate: "",
            projectIdentifier: backlog_id,
            create_At: "",
            errors: {}
        };

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentDidMount() {
        this.props.getProjectTask(this.state.projectIdentifier, this.state.projectSequence);
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.project_task) {
            const {
                id,
                summary,
                acceptanceCriteria,
                status,
                priority,
                dueDate,
                create_At
            } = nextProps.project_task;
            this.setState({
                id,
                summary,
                acceptanceCriteria,
                status,
                priority,
                dueDate,
                create_At
            });
        }
        if (nextProps.errors) {
            const {errors} = nextProps;
            this.setState({errors});
        }

    }

    onChange(e){
        this.setState({[e.target.name]: e.target.value});
    }

    onSubmit(e){
        e.preventDefault();
        const updatedProjectTask = {
            id:this.state.id,
            projectSequence: this.state.projectSequence,
            summary: this.state.summary,
            acceptanceCriteria: this.state.acceptanceCriteria,
            status: this.state.status,
            priority: this.state.priority,
            dueDate: this.state.dueDate,
            projectIdentifier: this.state.projectIdentifier
        };
        this.props.updateProjectTask(updatedProjectTask.projectIdentifier, updatedProjectTask.projectSequence, updatedProjectTask, this.props.history);
    }



    render() {
        const errors = this.state.errors;
        return (
            <div className="add-PBI">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <Link to={`/projectBoard/`} className="btn btn-light">
                                Back to Project Board
                            </Link>
                            <h4 className="display-4 text-center">Add /Update Project Task</h4>
                            <p className="lead text-center">Project Name + Project Code</p>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <input type="text"
                                           className={classnames("form-control form-control-lg", {
                                               "is-invalid": errors.summary
                                           })
                                           }
                                           name="summary"
                                           placeholder="Project Task summary"
                                           value={this.state.summary}
                                           onChange={this.onChange}
                                    />
                                    {
                                        errors.summary
                                            ? <div className="text-danger">{errors.summary}</div>
                                            : null
                                    }
                                </div>
                                <div className="form-group">
                                    <textarea className="form-control form-control-lg"
                                              placeholder="Acceptance Criteria"
                                              name="acceptanceCriteria"
                                              value={this.state.acceptanceCriteria}
                                              onChange={this.onChange}
                                    ></textarea>
                                </div>
                                <h6>Due Date</h6>
                                <div className="form-group">
                                    <input type="date"
                                           className="form-control form-control-lg"
                                           name="dueDate"
                                           // value={this.state.dueDate}
                                           // onChange={this.onChange}
                                    />
                                </div>
                                <div className="form-group">
                                    <select className="form-control form-control-lg"
                                            name="priority"
                                            value={this.state.priority}
                                            onChange={this.onChange}
                                    >
                                        <option value={0}>Select Priority</option>
                                        <option value={1}>High</option>
                                        <option value={2}>Medium</option>
                                        <option value={3}>Low</option>
                                    </select>
                                </div>

                                <div className="form-group">
                                    <select className="form-control form-control-lg"
                                            name="status"
                                            value={this.state.status}
                                            onChange={this.onChange}
                                    >
                                        <option value="">Select Status</option>
                                        <option value="TO_DO">TO DO</option>
                                        <option value="IN_PROGRESS">IN PROGRESS</option>
                                        <option value="DONE">DONE</option>
                                    </select>
                                </div>

                                <input type="submit" className="btn btn-primary btn-block mt-4"/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        );
    }
}

UpdateProjectTask.propTypes = {
    getProjectTask: PropTypes.func.isRequired,
    updateProjectTask: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired,
    project_task: PropTypes.object.isRequired
};

function mapStateToProps(state) {
    return {
        errors: state.errors,
        project_task: state.backlog.project_task
    }
}

function matchDispatchToProps(dispatch) {
    return bindActionCreators({
        getProjectTask: getProjectTask,
        updateProjectTask : updateProjectTask
    }, dispatch);
}

export default connect(mapStateToProps, matchDispatchToProps)(UpdateProjectTask);