import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import {Link} from 'react-router-dom';

class AuthorList extends Component {

    constructor(props) {
        super(props);
        this.state = {authors: [], isLoading: true};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('authors')
            .then(response => response.json())
            .then(data => this.setState({authors: data, isLoading: false}));
    }

    async remove(id) {
        await fetch(`/authors/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedAuthors = [...this.state.authors].filter(i => i.id !== id);
            this.setState({authors: updatedAuthors});
        });
    }

    render() {
        const {authors, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const authorList = authors.map(author => {
            const authorName = `${author.name || ''} ${author.patronymic || ''} ${author.surname || ''}`;
            return <tr key={author.id}>
                <td style={{whiteSpace: 'nowrap'}}>{authorName}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/authors/" + author.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(author.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/authors/new">Add Author</Button>
                    </div>
                    <h3>Authors</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th width="10%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {authorList}
                        </tbody>
                    </Table>
                    <Button color="link"><Link to="/">Back</Link></Button>
                </Container>
            </div>
        );
    }
}

export default AuthorList;