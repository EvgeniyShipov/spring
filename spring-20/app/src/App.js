import React, {Component} from 'react';
import './App.css';
import Home from './Home';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import BookList from './BookList';
import BookEdit from './BookEdit';
import AuthorList from './AuthorList';
import AuthorEdit from './AuthorEdit';
import JenreList from "./JenreList";
import JenreEdit from "./JenreEdit";
import CommentList from "./CommentList";
import CommentEdit from "./CommentEdit";

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home}/>
                    <Route path='/books' exact={true} component={BookList}/>
                    <Route path='/books/:id' component={BookEdit}/>
                    <Route path='/authors' exact={true} component={AuthorList}/>
                    <Route path='/authors/:id' component={AuthorEdit}/>
                    <Route path='/jenres' exact={true} component={JenreList}/>
                    <Route path='/jenres/:id' component={JenreEdit}/>
                    <Route path='/comments' exact={true} component={CommentList}/>
                    <Route path='/comments/:id' component={CommentEdit}/>
                </Switch>
            </Router>
        )
    }
}

export default App;