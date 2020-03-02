import React, {Component} from 'react';
import './App.css';
import {Link} from 'react-router-dom';
import {Button, Container} from 'reactstrap';

class Home extends Component {
    render() {
        return (
            <div>
                <Container fluid>
                    <Button color="link"><Link to="/books">Books</Link></Button>
                    <Button color="link"><Link to="/authors">Authors</Link></Button>
                    <Button color="link"><Link to="/jenres">Jenres</Link></Button>
                    <Button color="link"><Link to="/comments">Comments</Link></Button>
                </Container>
            </div>
        );
    }
}

export default Home;