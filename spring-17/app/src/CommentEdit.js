import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import CreatableSelect from 'react-select/creatable';

class CommentEdit extends Component {

    emptyItem = {
        book: '',
        message: ''
    };

    constructor(props) {
        super(props);
        this.state = {books: [], item: this.emptyItem};
        this.handleChange = this.handleChange.bind(this);
        this.onChange = this.onChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const comment = await (await fetch(`/comments/${this.props.match.params.id}`)).json();
            this.setState({item: comment});
        }

        const books = await (await fetch(`/books`)).json();
        this.setState({
            books: books.map(book => {
                const bookTitle = `${book.title || ''}`;
                return {value: book, label: bookTitle}
            })
        });
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let {item} = this.state;
        item[name] = value;
        this.setState({item: item});
    }

    onChange(value) {
        let {item} = this.state;
        item.book = value;
        this.setState({item: item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        await fetch('/comments', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/comments');
    }

    render() {
        const {books, item} = this.state;
        const title = <h2>{item.id ? 'Edit Comment' : 'Add Comment'}</h2>;

        return <div>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="book">Book</Label>
                        <CreatableSelect options={books} onChange={this.onChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="message">Message</Label>
                        <Input type="text" name="message" id="message" value={item.message || ''}
                               onChange={this.handleChange} autoComplete="message"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/comments">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(CommentEdit);