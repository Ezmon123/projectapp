import React, {Component} from "react";
import {Link} from "react-router-dom";
import PropTypes from "prop-types";
import {connect} from "react-redux";
import Backlog from "./Backlog";
import {getBacklog} from "../../actions/backlogActions";
import {bindActionCreators} from "redux";

class ProjectBoard extends Component {
    constructor() {
        super();
        this.state = {
            errors: {}
        };
    }

    componentDidMount() {
        const {id} = this.props.match.params;
        this.props.getBacklog(id)
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.errors) {
            this.setState({errors: nextProps.errors})
        }
    }

    render() {
        const {id} = this.props.match.params;
        const {project_tasks} = this.props.backlog;
        const {errors} = this.state;
        let BoardContent;

        const boardAlgorithm = (errors, project_tasks) => {
            if (project_tasks.length < 1) {
                if (errors.projectIdentifier) {
                    return (
                        <div className="alert alert-danger text-center" role="alert">
                            {errors.projectIdentifier}
                        </div>
                    );
                } else {
                    return (
                        <div className="alert alert-info text-center" role="alert">
                            No Project Tasks on this board!
                        </div>
                    )
                }
            } else {
                return (
                    <Backlog project_tasks_prop={project_tasks}/>
                )
            }
        };

        BoardContent = boardAlgorithm(errors, project_tasks);
        return (
            <div className="container">
                <Link to={`/addProjectTask/${id}`} className="btn btn-primary mb-3">
                    <i className="fas fa-plus-circle"> Create Project Task</i>
                </Link>
                <br/>
                <hr/>
                {BoardContent}
                {/*<Backlog project_tasks_prop={project_tasks}/>*/}

            </div>
        )
    }
}

ProjectBoard.propTypes = {
    getBacklog: PropTypes.func.isRequired,
    backlog: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired
};

function mapStateToProps(state) {
    return ({
        backlog: state.backlog,
        errors: state.errors
    })
}

function matchDispatchToProps(dispatch) {
    return bindActionCreators({
        getBacklog: getBacklog
    }, dispatch)
}

export default connect(mapStateToProps, matchDispatchToProps)(ProjectBoard);