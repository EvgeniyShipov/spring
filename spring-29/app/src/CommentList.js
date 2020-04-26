import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import {Link} from 'react-router-dom';

class CommentList extends Component {

    constructor(props) {
        super(props);
        this.state = {comments: [], isLoading: true};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('comments')
            .then(response => response.json())
            .then(data => this.setState({comments: data, isLoading: false}));
    }

    async remove(id) {
        await fetch(`/comments/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedComments = [...this.state.comments].filter(i => i.id !== id);
            this.setState({comments: updatedComments});
        });
    }

    render() {
        const {comments, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const commentList = comments.map(comment => {
            const bookTitle = `${comment.book.title || ''}`;
            return <tr key={comment.id}>
                <td style={{whiteSpace: 'nowrap'}}>{bookTitle}</td>
                <td>{comment.message}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/comments/" + comment.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(comment.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/comments/new">Add Comment</Button>
                    </div>
                    <h3>Comments</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th>Book</th>
                            <th>Message</th>
                            <th width="10%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {commentList}
                        </tbody>
                    </Table>
                    <Button color="link"><Link to="/">Back</Link></Button>
                </Container>
            </div>
        );
    }
}

export default CommentList;